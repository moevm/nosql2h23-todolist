export default {
    namespaced: true,
    state: {
        isVisible: false,
        alertMessage: '',
        timeoutValue: 10000,
        timeout: null,
    },
    getters: {
    },
    mutations: {
        hideAlert(state){
            state.isVisible = false;
            clearTimeout(state.timeout);
        },
        showAlert(state){
            state.isVisible = true;
        },
        setAlertMessage(state, payload) {
            state.alertMessage = payload.message;
        },
        setTimeout(state, payload) {
            state.timeout = payload.timeout;
        }
    },
    actions: {
        hideAlert(store) {
            store.commit('hideAlert');
        },
        showAlert(store, payload) {
            store.commit('setAlertMessage', payload)
            store.commit('showAlert');
            store.commit('setTimeout', {timeout: setTimeout(() => {
                store.commit('hideAlert');
            }, store.state.timeoutValue)});
        }
    }
}
