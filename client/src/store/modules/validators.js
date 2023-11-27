export default {
    namespaced: true,
    state: {
        taskInputRules: [
            value => !!value || 'Required.',
            value => (value && value.length >= 3) || 'Min 3 characters',
        ]
    },
    getters: {
    },
    mutations: {
    },
    actions: {
    }
}
