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
        name: 'COMPLETE',
        text: 'Завершенные',
      },
      {
        name: 'INCOMPLETE',
        text: 'Незавершенные',
      }
    ],
    currentFilter: {
      name: 'all',
      text: 'Все',
    },
    projects: [],
    activeProject: {},
    persons: [],
  },
  getters: {
    project: state => state.activeProject,
  },
  mutations: {
    setPersons(state, persons) {
      state.persons = [...persons];
    },
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
    setActiveProjectById(state, project_id) {
      state.activeProject = {...state.projects.find((el) => el.id === project_id)};
    },
    addTask(state, task) {
      state.activeProject.tasks.push(task);
    },
    removeTask(state, task_id) {
      state.activeProject.tasks = state.activeProject.tasks.filter((el) => el.id !== task_id);
    },
    editTask(state, task) {
      const index = state.activeProject.tasks.findIndex((el) => el.id === task.id);
      state.activeProject.tasks[index] = {...task};
    }
  },
  actions: {
    getActiveProject: async ({ commit}, id) => {
      await service.findProjectById(id)
        .then((res) => {
          commit('setActiveProject', res);
        })
        .catch((e) => console.log(e));
    },
    addTask: async ({ commit }, task) => {
      await service.getAllProjects().then((res) => commit('setProjects', res)).catch((e) => console.log(e));
      commit('addTask', task);
    },
    removeTask: async ({ commit }, task_id) => {
      await service.getAllProjects().then((res) => commit('setProjects', res)).catch((e) => console.log(e));
      commit('removeTask', task_id);
    },
    updateTask: async ({ commit }, project_id) => {
      await service.getAllProjects().then((res) => commit('setProjects', res)).catch((e) => console.log(e));
      commit('setActiveProjectById', project_id);
    },
    addSubTask: async ({ commit }, project_id) => {
      await service.getAllProjects().then((res) => commit('setProjects', res)).catch((e) => console.log(e));
      commit('setActiveProjectById', project_id);
    },
  },
  modules: {
    alert,
    validators
  },
  strict: process.env.NODE_ENV !== 'production'
})
