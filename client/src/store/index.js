import Vue from 'vue'
import Vuex from 'vuex'
import service from '../logic/services/projectService';

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
    projects: [],
    activeProject: {},
  },
  getters: {},
  mutations: {
    setUser(state, user) {
      state.user = user;
      state.userRole = user.role === 'ROLE_ADMIN' ? 'admin' : 'user';
    },
    setFilter(state, filter) {
      state.currentFilter = filter;
    },
    setProjects(state, projects) {
      state.projects = projects;
    },
    addProject(state, project) {
      state.projects.push(project);
    },
    deleteProject(state, id) {
      state.projects = state.projects.filter((el) => el.id !== id);
    },
    setActiveProject(state, project) {
      state.activeProject = {...project};
    },
  },
  actions: {
    getActiveProject: async ({ commit}, id) => {
      await service.findProjectById(id)
        .then((res) => {
          commit('setActiveProject', res);
        })
        .catch((e) => console.log(e));
    },
  },
  modules: {
    alert,
    validators
  },
  strict: process.env.NODE_ENV !== 'production'
})
