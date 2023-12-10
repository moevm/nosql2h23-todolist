import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import alert from './modules/alert.js';
import validators from "./modules/validators.js";

export default new Vuex.Store({
  state: {
    user: {},
    userRole: 'admin',
    filters: [
      {
        name: 'all',
        text: 'Все',
      },
      {
        name: 'complete',
        text: 'Завершенные',
      },
      {
        name: 'incomplete',
        text: 'Незавершенные',
      }
    ],
    currentFilter: {
      name: 'all',
      text: 'Все',
    },
  },
  getters: {},
  mutations: {
    setUser(state, user) {
      state.user = user;
      state.userRole = user.role;
    },
    setFilter(state, filter) {
      state.currentFilter = filter;
    }
  },
  actions: {},
  modules: {
    alert,
    validators
  },
  strict: process.env.NODE_ENV !== 'production'
})
