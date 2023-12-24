const getToken = () => {
  return localStorage.getItem('user');
};

const getAuthHeader = () => {
  return `Bearer ${getToken()}`;
};

export default class Api {
  static baseUrl = 'http://localhost:8080';

  // АВТОРИЗАЦИЯ И АУТЕНТИФИКАЦИЯ
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
      body: JSON.stringify(data),
    }).then((response) => {
      if (response.status !== 200) {
        return Promise.reject(new Error(`${response.status} ${response.statusText}`));
      }
      return Promise.resolve(response);
    }).then((res) => res.json());
  }

  // РАБОТА С ПОЛЬЗОВАТЕЛЯМИ
  static getPersons() {
    return fetch(`${this.baseUrl}/person/all`, {
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

  // РАБОТА С ПРОЕКТАМИ
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

  static findProjectByName(project_name) {
    return fetch(`${this.baseUrl}/project/find_name/${project_name}`, {
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

  static findProjectById(project_id) {
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

  static findProjectsByPerson(person_id) {
    return fetch(`${this.baseUrl}/project/find_person/${person_id}`, {
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

  static postPersonForProject(project_id, person_id) {
    return fetch(`${this.baseUrl}/project/${project_id}/person/${person_id}`, {
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

  static removePersonFromProject(project_id, person_id) {
    return fetch(`${this.baseUrl}/project/${project_id}/person/${person_id}`, {
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

  static editProject(project_id, data) {
    return fetch(`${this.baseUrl}/project/${project_id}`, {
      method: 'PATCH',
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

  // РАБОТА С ТАСКАМИ
  static getProjectTasks(project_id) {
    return fetch(`${this.baseUrl}/tasks/${project_id}`, {
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

  static addNewTask(project_id, data) {
    return fetch(`${this.baseUrl}/tasks/${project_id}`, {
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

  static removeTask(project_id, task_id) {
    return fetch(`${this.baseUrl}/tasks/${project_id}/${task_id}`, {
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

  static editTask(project_id, task_id, data) {
    return fetch(`${this.baseUrl}/tasks/${project_id}/${task_id}`, {
      method: 'PATCH',
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

  // РАБОТА С ПОДТАСКАМИ
  static getSubtasksForTask(task_id) {
    return fetch(`${this.baseUrl}/subtasks/${task_id}`, {
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

  static addNewSubtask(project_id, task_id, data) {
    return fetch(`${this.baseUrl}/subtasks/${project_id}/${task_id}/add-subtask`, {
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

  static removeSubtask(project_id, task_id, subtask_id) {
    return fetch(`${this.baseUrl}/subtasks/${project_id}/${task_id}/${subtask_id}`, {
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

  static editSubtask(project_id, task_id, subtask_id, data) {
    return fetch(`${this.baseUrl}/subtasks/${project_id}/${task_id}/${subtask_id}`, {
      method: 'PATCH',
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

  // ИМПОРТ И ЭКСПОРТ
  static importDb() {
    return fetch(`${this.baseUrl}/data/download`, {
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
    }).then( res => res.json() )
      .then( async (res) => {
        const file = JSON.stringify(res);
        const fileHandle = await window.showSaveFilePicker({
          // рекомендуемое название файла
          suggestedName: 'ToDoList.json',
          types: [
            {
              description: "JSON file",
              accept: { "application/json": [".json"] },
            },
          ],
        })
        const writableStream = await fileHandle.createWritable()

        await writableStream.write(file)
        // данный метод не упоминается в черновике спецификации,
        // хотя там говорится о необходимости закрытия потока
        // для успешной записи файла
        await writableStream.close()
      });
  }

  static exportDb(data) {
    return fetch(`${this.baseUrl}/data/load`, {
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
}
