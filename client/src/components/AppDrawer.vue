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
          :to="{name: 'projects', params: {projectName: project.name}}"
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
  data() {
    return {
      projects: [{
        name: 'Проект №1',
        executers: [],
        status: 'Открыт',
        tasks: [],
        log: {},
      }]
    }
  },
  methods: {
    onToggle(value) {
      this.$emit('update:isToggled', value);
    }
  }
}
</script>
