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
            <v-icon color="green">mdi-circle</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title v-text="project.name"/>
          </v-list-item-content>
          <v-list-item-icon>
            <span>1/3</span>
          </v-list-item-icon>
        </v-list-item>
        <v-list-item
          class="pl-5 new-project-item"
          link
          @click="addNewProject"
        >
          <v-list-item-content>
            <v-list-item-title v-text="'Add New Project'"/>
          </v-list-item-content>
        </v-list-item>
      </v-list-group>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: "AppDrawer",
  props: {
    isToggled: {
      type: Boolean,
      required: true
    }
  },
  inject: ['projectService'],
  data() {
    return {
      projects: [],
    }
  },
  created() {
    this.projectService.getAllProjects().then((res) => {
     console.log(res);
    }).catch((e) => console.log(e));
  },
  methods: {
    onToggle(value) {
      this.$emit('update:isToggled', value);
    },
    addNewProject() {
      this.projectService.createProject({name: 'New Project'}).then((res) => {
        console.log(res);
      }).catch((e) => e);
      const newId = Math.random();
      this.projects.push({
        id: newId,
        name: 'Проект №2',
        executers: [],
        status: 'Открыт',
        tasks: [],
        log: {},
      })
      this.$router.push(`/projects/${newId}`)
    }
  }
}
</script>

<style scoped>
.new-project-item {
  color: rgba(25, 118, 210, 0.98) !important;
  border: 2px dashed #1976d266;
}
</style>
