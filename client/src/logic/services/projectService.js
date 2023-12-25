import Api from '@/logic/api';

export default class ProjectService {

  // АВТОРИЗАЦИЯ И АУТЕНТИФИКАЦИЯ
  static async login(data) {
    return Api.login(data);
  }

  static async register(data) {
    return Api.register(data);
  }

  // РАБОТА С ПОЛЬЗОВАТЕЛЯМИ
  static async getPersons() {
    return Api.getPersons();
  }

  // РАБОТА С ПРОЕКТАМИ
  static async getAllProjects() {
    return Api.getAllProjects();
  }

  static async getAllProjectsAtPage(page, size) {
    return Api.getAllProjectsAtPage(page, size);
  }

  static async findProjectByName(pr_name) {
    return Api.findProjectByName(pr_name);
  }

  static async findProjectById(id) {
    return Api.findProjectById(id);
  }

  static async findProjectsByPerson(person_id) {
    return Api.findProjectsByPerson(person_id);
  }

  static async createProject(data) {
    return Api.postCreateProject(data);
  }

  static async setPersonToProject(project_id, person_id) {
    return Api.postPersonForProject(project_id, person_id);
  }

  static async deleteProject(project_id) {
    return Api.deleteProject(project_id);
  }

  static async removePersonFromProject(project_id, person_id) {
    return Api.removePersonFromProject(project_id, person_id);
  }

  static async editProject(project_id, data) {
    return Api.editProject(project_id, data);
  }

  // РАБОТА С ТАСКАМИ
  static async getProjectTasks(project_id) {
    return Api.getProjectTasks(project_id);
  }

  static async addNewTask(project_id, task) {
    return Api.addNewTask(project_id, task);
  }

  static async removeTask(project_id, task_id) {
    return Api.removeTask(project_id, task_id);
  }

  static async editTask(project_id, task_id, data) {
    return Api.editTask(project_id, task_id, data);
  }

  static async getAllTasks() {
    return Api.getAllTasks();
  }

  static async getTaskById(task_id) {
    return Api.getTaskById(task_id);
  }
  static async saveTask(data) {
    return Api.postSaveTask(data);
  }

  // РАБОТА С ПОДТАСКАМИ
  static async getSubtasksForTask(task_id) {
    return Api.getSubtasksForTask(task_id);
  }

  static async addNewSubtask(project_id, task_id, data) {
    return Api.addNewSubtask(project_id, task_id, data);
  }

  static async removeSubtask(project_id, task_id, subtask_id) {
    return Api.removeSubtask(project_id, task_id, subtask_id);
  }

  static async editSubtask(project_id, task_id, subtask_id, data) {
    return Api.editSubtask(project_id, task_id, subtask_id, data);
  }

  // ИМПОРТ И ЭКСПОРТ
  static async importDb() {
    return Api.importDb();
  }

  static async exportDb(data) {
    return Api.exportDb(data);
  }
}
