const { reactive } = Vue;

// Store holding an array of active toasts
const toastStore = reactive({
    toasts: []
});

function useToast() {
    const showToast = (message, type = 'error', duration = 3000) => {
        const id = Date.now() + Math.random(); // Unique ID for each toast

        // Auto-remove after duration
        const timer = setTimeout(() => {
            removeToast(id);
        }, duration);

        // Push new toast to the array so they stack up
        toastStore.toasts.push({
            id,
            message,
            type,
            timer
        });
    };

    const removeToast = (id) => {
        const index = toastStore.toasts.findIndex(t => t.id === id);
        if (index !== -1) {
            clearTimeout(toastStore.toasts[index].timer);
            toastStore.toasts.splice(index, 1);
        }
    };

    return { toastStore, showToast, removeToast };
}

// Global Toast Stacking Component Definition
const ToastNotification = {
    name: 'ToastNotification',
    setup() {
        const { toastStore, removeToast } = useToast();
        return { toastStore, removeToast };
    },
    template: `
      <!-- Fixed container at top-right for stacking -->
      <div class="fixed top-5 right-5 z-50 flex flex-col space-y-3 w-full max-w-md pointer-events-none px-4 sm:px-0">
          <transition-group
              enter-active-class="transition duration-300 ease-out"
              enter-from-class="translate-y-2 opacity-0 scale-95"
              enter-to-class="translate-y-0 opacity-100 scale-100"
              leave-active-class="transition duration-200 ease-in"
              leave-from-class="translate-y-0 opacity-100 scale-100"
              leave-to-class="translate-y-2 opacity-0 scale-95"
          >
              <div 
                  v-for="toast in toastStore.toasts" 
                  :key="toast.id"
                  class="pointer-events-auto flex items-center w-full p-4 text-sm rounded-xl shadow-xl border backdrop-blur-md transition-all"
                  :class="[
                      toast.type === 'error' 
                          ? 'bg-red-50/95 text-red-900 border-red-200 dark:bg-gray-950/95 dark:text-red-300 dark:border-red-900' 
                          : 'bg-emerald-50/95 text-emerald-900 border-emerald-200 dark:bg-gray-950/95 dark:text-emerald-300 dark:border-emerald-900'
                  ]"
                  role="alert"
              >
                  <!-- Icon Wrapper -->
                  <div class="inline-flex items-center justify-center flex-shrink-0 w-8 h-8 rounded-lg"
                       :class="toast.type === 'error' ? 'bg-red-100 text-red-600 dark:bg-red-900/50 dark:text-red-300' : 'bg-emerald-100 text-emerald-600 dark:bg-emerald-900/50 dark:text-emerald-300'">
                      <svg v-if="toast.type === 'error'" class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">
                          <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 11.793a1 1 0 1 1-1.414 1.414L10 11.414l-2.293 2.293a1 1 0 0 1-1.414-1.414L8.586 10 6.293 7.707a1 1 0 0 1 1.414-1.414L10 8.586l2.293-2.293a1 1 0 0 1 1.414 1.414L11.414 10l2.293 2.293Z"/>
                      </svg>
                      <svg v-else class="w-5 h-5" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20">
                          <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"/>
                      </svg>
                  </div>

                  <!-- Text Content -->
                  <div class="ms-3 text-sm font-medium flex-1">
                      <span class="font-semibold block">{{ toast.type === 'error' ? 'Action Failed' : 'Success' }}</span>
                      {{ toast.message }}
                  </div>

                  <!-- Manual Dismiss Button -->
                  <button 
                      type="button" 
                      @click="removeToast(toast.id)"
                      class="ms-auto -mx-1.5 -my-1.5 bg-transparent rounded-lg p-1.5 inline-flex items-center justify-center h-8 w-8 transition-colors hover:bg-black/5 dark:hover:bg-white/10"
                      aria-label="Close"
                  >
                      <span class="sr-only">Close</span>
                      <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M1 7l6-6M1 7l6 6"/>
                      </svg>
                  </button>
              </div>
          </transition-group>
      </div>
      `
};