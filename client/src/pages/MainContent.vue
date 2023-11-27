<template>
  <div>
    <v-app-bar
        app
        clipped-left
    >
      <v-app-bar-nav-icon @click="onDrawerToggle"/>
      <v-app-bar-title>TODO App</v-app-bar-title>
      <v-spacer/>
      <v-text-field
          outlined
          filled
          rounded
          hide-details
          label="Сотрудник или проект"
          prepend-inner-icon="mdi-magnify"
          dense
          style="max-width: 270px"
      ></v-text-field>
      <DatabaseDialog/>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <div v-bind="attrs"
               v-on="on"
          >
            <v-switch
              v-model="$vuetify.theme.dark"
              inset
              prepend-icon="mdi-theme-light-dark"
              hide-details="true"
            />
          </div>
        </template>
        <span>Сменить тему на {{$vuetify.theme.dark ? 'светлую' : 'темную'}}</span>
      </v-tooltip>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            :to="{name: 'auth'}"
            plain
            icon
          >
            <v-icon v-bind="attrs" v-on="on">mdi-logout</v-icon>
          </v-btn>

        </template>
        <span>Выйти из системы</span>
      </v-tooltip>
    </v-app-bar>
    <AppDrawer :is-toggled.sync="drawerToggled"/>

    <v-main>
      <v-container
          class="fill-height align-content-start"
          fluid
      >
        <router-view/>
      </v-container>
    </v-main>
  </div>
</template>

<script>
import {defineComponent} from 'vue'
import AppDrawer from '@/components/AppDrawer.vue';
import DatabaseDialog from '@/components/DatabaseDialog.vue';

export default defineComponent({
  name: "MainContent",
  components: {DatabaseDialog, AppDrawer},
  data: () => ({
    drawerToggled: true,
  }),
  methods: {
    onDrawerToggle(){
      this.drawerToggled = !this.drawerToggled;
    }
  }
})
</script>

<style scoped>

</style>