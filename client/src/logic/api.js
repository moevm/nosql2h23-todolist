const getToken = () => {
  return localStorage.getItem('user');
};

const getAuthHeader = () => {
  return `Bearer ${getToken()}`;
};

export default class Api {
  static baseUrl = 'http://localhost:8080';

  static login(data) {
    return fetch(`${this.baseUrl}/auth/login`, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static register(data) {
    return fetch(`${this.baseUrl}/auth/registration`, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: data,
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static getAllProjects() {
    return fetch(`${this.baseUrl}/project/all`, {
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthHeader(),
      },
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static postAllProjectsForPerson(person_id) {
    return fetch(`${this.baseUrl}/projects/find_person/${person_id}`, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
        'Authorization': getAuthHeader(),
      },
    }).then(response => Promise.all([response, response.json()]))
      .then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static postCreateProject(data) {
    return fetch(`${this.baseUrl}/project/create`, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
        Authorization: getAuthHeader(),
      },
      body: JSON.stringify(data),
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static deleteProject(project_id) {
    return fetch(`${this.baseUrl}/project/${project_id}`, {
      method: 'DELETE',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
        Authorization: getAuthHeader(),
      },
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static findProject(project_id) {
    return fetch(`${this.baseUrl}/project/find/${project_id}`, {
      method: 'GET',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
        Authorization: getAuthHeader(),
      },
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static patchProject(project_id, data) {
    return fetch(`${this.baseUrl}/project/admin/${project_id}`, {
      method: 'PATCH',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: data,
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static postPersonForProject(project_id, person_id) {
    return fetch(`${this.baseUrl}/project/admin/${project_id}/person/${person_id}`, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
        Authorization: getAuthHeader(),
      },
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static deletePersonForProject(project_id, person_id) {
    return fetch(`${this.baseUrl}/project/admin/${project_id}/person/${person_id}`, {
      method: 'DELETE',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static postFind(data) {
    return fetch(`${this.baseUrl}/project/find`, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: data
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static getAllTasks() {
    return fetch(`${this.baseUrl}/tasks/all`, {
      headers: {
        Authorization: getAuthHeader(),
      }
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static getTaskById(task_id) {
    return fetch(`${this.baseUrl}/tasks/${task_id}`).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static postSaveTask(data) {
    return fetch(`${this.baseUrl}/tasks/save-task`, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: data
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  static postAddSubTask(data) {
    return fetch(`${this.baseUrl}/tasks/add-subtask`, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
      body: data
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }
}
