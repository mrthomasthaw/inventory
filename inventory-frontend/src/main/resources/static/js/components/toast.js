// toast.js
import { reactive } from 'vue'

const toastState = reactive({
    show: false,
    message: '',
    type: 'error', // 'error' or 'success'
    timer: null
})

export function useToast() {
    const showToast = (message, type = 'error', duration = 5000) => {
        toastState.message = message
        toastState.type = type
        toastState.show = true

        if (toastState.timer) clearTimeout(toastState.timer)

        toastState.timer = setTimeout(() => {
            toastState.show = false
        }, duration)
    }

    const hideToast = () => {
        toastState.show = false
        if (toastState.timer) clearTimeout(toastState.timer)
    }

    return {
        toastState,
        showToast,
        hideToast
    }
}