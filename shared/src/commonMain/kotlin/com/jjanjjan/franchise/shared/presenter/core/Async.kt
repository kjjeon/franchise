package com.jjanjjan.franchise.shared.presenter.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.setValue
import com.slack.circuit.retained.rememberRetained
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * 비동기 작업의 결과를 나타내는 sealed 인터페이스.
 */
@Stable
sealed interface Async<out T> {

    /**
     * 초기화되지 않은 상태를 나타냅니다.
     */
    @Stable
    data object Uninitialized : Async<Nothing>

    /**
     * 로딩 중 상태를 나타냅니다.
     * 비동기 작업이 진행 중임을 의미합니다.
     *
     * @property producer 비동기 작업을 실행하는 프로듀서.
     */
    @Stable
    data class Loading<T>(
        val producer: AsyncProducer<T> = AsyncProducer(),
    ) : Async<T>

    /**
     * 비동기 작업이 실패한 상태를 나타냅니다.
     * 오류 정보와 선택적으로 값을 포함할 수 있습니다.
     *
     * @property error 발생한 오류.
     * @property value 실패 상태에서 선택적으로 포함될 수 있는 값.
     */
    @Stable
    data class Fail<out T>(val error: Throwable, val value: T? = null) : Async<T>

    /**
     * 비동기 작업이 성공한 상태를 나타냅니다.
     * 결과 값을 포함합니다.
     *
     * @property result 작업의 성공 결과.
     */
    @Stable
    data class Success<out T>(
        val result: T,
    ) : Async<T>

    /**
     * 비동기 상태를 생성하기 위한 함수 인터페이스.
     */
    fun interface StateProducer<T> {

        /**
         * 비동기 상태를 생성합니다.
         *
         * @param async 현재의 비동기 상태.
         * @param onSuccess 작업이 성공했을 때 호출될 콜백 함수.
         * @param onFail 작업이 실패했을 때 호출될 콜백 함수.
         * @param producer 비동기 작업을 수행할 함수.
         * @return [producer] 작업을 완료한 후의 상태 값
         */
        @Composable
        operator fun invoke(
            async: Async<T>,
            onSuccess: ((T) -> Async<T>)?,
            onFail: ((Throwable, T?) -> Async<T>)?,
            producer: suspend () -> T,
        ): Async<T>
    }
}

/**
 * 비동기 상태를 관리하는 클래스. 비동기 작업의 실행 및 결과 처리를 담당합니다.
 */
class AsyncProducer<T> : Async.StateProducer<T> {

    /**
     * 비동기 상태를 생성합니다.
     *
     * @param async 현재의 비동기 상태.
     * @param onSuccess 작업이 성공했을 때 호출될 콜백 함수.
     * @param onFail 작업이 실패했을 때 호출될 콜백 함수.
     * @param producer 비동기 작업을 수행할 함수.
     * @return [producer] 작업을 완료한 후의 상태 값
     */
    @Composable
    override fun invoke(
        async: Async<T>,
        onSuccess: ((T) -> Async<T>)?,
        onFail: ((Throwable, T?) -> Async<T>)?,
        producer: suspend () -> T,
    ): Async<T> {
        var throwable: Throwable? by rememberRetained {
            mutableStateOf(null)
        }

        val result by produceState<T?>(
            initialValue = null,
            key1 = this,
        ) {
            try {
                value = producer()
            } catch (c: CancellationException) {
                throw c
            } catch (e: Throwable) {
                throwable = e
                value = null
            }
            awaitDispose {
                // 작업 종료 시 처리 추가 필요할 시
            }
        }

        result?.let {
            onSuccess?.let { callback -> return callback(it) }
            return Async.Success(it)
        }

        throwable?.let {
            onFail?.let { callback -> return callback(it, null) }
            return Async.Fail(it, null)
        }

        return async
    }
}

/**
 * [Async] 상태가 [Async.Success]일 경우 결과를 반환하고, 그렇지 않으면 `null`을 반환합니다.
 *
 * @return [Async.Success] 상태의 결과 또는 `null`.
 */
fun <T> Async<T>.getOrNull() = if (this is Async.Success) result else null

/**
 * [Async] 상태가 [Async.Success]일 경우 결과를 반환하고, 그렇지 않으면 [defaultValue]을 반환합니다.
 *
 * @param defaultValue [Async.Success] 상태가 아닐 경우 반환할 기본값.
 * @return [Async.Success] 상태의 결과 또는 기본값.
 */
fun <R, T : R> Async<T>.getOrDefault(defaultValue: R): R =
    if (this !is Async.Success) defaultValue else result

/**
 * 비동기 작업을 시작하고 그 결과를 Async 상태로 반환하는 Composable 함수.
 *
 * @param async 현재의 비동기 상태.
 * @param onSuccess 작업이 성공했을 때 호출될 콜백 함수.
 * @param onFail 작업이 실패했을 때 호출될 콜백 함수.
 * @param producer 비동기 작업을 수행할 함수.
 * @return [producer] 작업을 완료한 후의 상태 값
 */
@Composable
fun <T> producerAsync(
    async: Async<T>,
    onSuccess: ((T) -> Async<T>)? = null,
    onFail: ((Throwable, T?) -> Async<T>)? = null,
    producer: suspend () -> T,
): Async<T> = when (async) {
    is Async.Loading -> async.producer.invoke(
        async = async,
        onSuccess = onSuccess,
        onFail = onFail,
        producer = producer,
    )

    else -> async
}

/**
 * [scope] 내에서 비동기 작업을 수행하고 결과를 처리하는 함수.
 *
 * @param T 비동기 작업의 결과 타입.
 * @param scope [CoroutineScope] 인스턴스. 이 스코프 내에서 코루틴이 실행됩니다.
 * @param dispatcher 코루틴이 실행될 [CoroutineDispatcher]. 작업이 수행될 스레드 또는 스레드 풀을 결정합니다.
 * @param producer 비동기 작업을 수행할 함수. 이 함수는 suspend 함수로, 코루틴 내에서 실행됩니다.
 * @return Job 인스턴스. 이를 통해 코루틴의 실행을 관리할 수 있습니다.
 */
fun <T> producerAsync(
    scope: CoroutineScope,
    dispatcher: CoroutineDispatcher,
    producer: suspend () -> T,
) = scope.launch(dispatcher) {
    producer()
}
