<template>
  <v-navigation-drawer
    app
    clipped
    fixed
    mobile-breakpoint="960"
    style="border-right: 1px solid rgba(0, 0, 0, 0.2)"
    :model-value="true"
    :value="isToggled"
    @input="onToggle"
  >
    <v-list nav>
      <v-list-item
        to="/"
        link
      >
        <v-list-item-icon>
          <v-icon>mdi-home</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>Главная</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item
        to="/stats"
        link
      >
        <v-list-item-icon>
          <v-icon>mdi-chart-gantt</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>Анализ</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-group
        :value="true"
        no-action
      >
        <template v-slot:activator>
          <v-list-item-icon>
            <v-icon>mdi-sitemap</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>Проекты</v-list-item-title>
          </v-list-item-content>
        </template>
        <v-list-item
          class="pl-5"
          v-for="(project, index) in projects"
          :to="{name: 'projects', params: {id: project.id}}"
          :key="index"
          link
        >
          <v-list-item-icon>
            <v-icon :color="project.status === 'IN_PROGRESS' ? 'green lighten-1' : 'red lighten-1'">mdi-circle</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title v-text="project.name"/>
          </v-list-item-content>
          <v-list-item-icon>
            <span>{{completedTaskCount(project.tasks)}}</span>
          </v-list-item-icon>
        </v-list-item>
        <span v-if="projects && projects.length === 0" class="d-flex justify-center">
          Нет активных проектов
        </span>
        <v-list-item
          class="pl-2 new-project-item"
          link
        >
          <v-text-field
            v-if="$store.state.userRole === 'admin'"
            v-model="newProjectName"
            :color="inputStateColor"
            placeholder="Добавить новый проект"
            text
          >
            <template v-slot:append>
              <v-icon
                @click="onSubmit('Проект успешно создан!')"
                :disabled="!isInputValid"
                :color="inputStateColor"
              >
                mdi-plus
              </v-icon>
            </template>
          </v-text-field>
        </v-list-item>
      </v-list-group>
    </v-list>
    <ConfirmAlert ref="confirmMainTaskAddDialogue"/>
  </v-navigation-drawer>
</template>

<script>
import {mapActions, mapMutations, mapState} from "vuex";
import ConfirmAlert from "@/components/ConfirmAlert.vue";

export default {
  name: "AppDrawer",
  components: {ConfirmAlert},
  props: {
    isToggled: {
      type: Boolean,
      required: true
    }
  },
  inject: ['projectService'],
  data() {
    return {
      newProjectName: '',
    }
  },
  computed: {
    ...mapState(['projects']),
    isInputValid() {
      return this.newProjectName.length > 3;
    },
    inputStateColor() {
      return !this.isInputValid ? 'grey' : 'green';
    }
  },
  created() {
    this.projectService.getAllProjects().then((res) => {
      this.setProjects(res || []);
    }).catch((e) => console.error(e));
  },
  methods: {
    ...mapActions('alert', {
      hideAlert: "hideAlert",
      showAlert: "showAlert"
    }),
    ...mapActions(['getActiveProject']),
    ...mapMutations(['addProject', 'setProjects', 'setActiveProject']),
    onToggle(value) {
      this.$emit('update:isToggled', value);
    },
    completedTaskCount(tasks) {
      const count = tasks?.filter((el) => el.status === 'COMPLETE')?.length || 0;
      return `${count}/${tasks?.length || 0}`;
    },
    async onSubmit(alertMessage) {
      await this.hideAlert();
      const isConfirmed = await this.$refs.confirmMainTaskAddDialogue.show({
        message: "Are you sure?"
      });
      if (isConfirmed) {
        if (this.projects.some((el) => el.name === this.newProjectName)) {
          await this.showAlert({message: 'Проект с таким именем уже существует', type: 'error'});
        } else {
          this.projectService.createProject({name: this.newProjectName}).then((res) => {
            this.newProjectName = '';
            this.showAlert({message: alertMessage});
            this.setActiveProject(res);
            this.addProject(res);
            this.$router.push(`/projects/${res.id}`)
          }).catch(() => this.showAlert({message: 'Проект с таким именем уже существует', type: 'error'}));
        }
      }
    },
  }
}
</script>
