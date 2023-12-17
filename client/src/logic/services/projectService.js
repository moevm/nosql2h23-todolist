import Api from '@/logic/api';

export default class ProjectService {

  static async login(data) {
    return Api.login(data);
  }

  static async register(data) {
    return Api.register(data);
  }

  static async getAllProjectsAdmin() {
    return Api.getAllProjectsAdmin();
  }

  static async getAllProjects() {
    return Api.getAllProjects();
  }

  static async postAllProjectsForPerson(person_id) {
    return Api.postAllProjectsForPerson(person_id);
  }

  static async createProject(data) {
    return Api.postCreateProject(data);
  }

  static async deleteProject(project_id) {
    return Api.deleteProject(project_id);
  }

  static async editProject(project_id, data) {
    return Api.patchProject(project_id, data);
  }

  static async postPersonForProject(project_id, person_id) {
    return Api.postPersonForProject(project_id, person_id);
  }

  static async deletePersonForProject(project_id, person_id) {
    return Api.deletePersonForProject(project_id, person_id);
  }

  static async findProject(data) {
    return Api.postFind(data);
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

  static async addSubTask(data) {
    return Api.postAddSubTask(data);
  }
}
