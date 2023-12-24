<template>
  <div>
    <v-app-bar
        app
        clipped-left
    >
      <v-app-bar-nav-icon @click="onDrawerToggle"/>
      <v-app-bar-title>TODO App</v-app-bar-title>
      <v-spacer/>
      <v-combobox
        class="app-bar_search"
        outlined
        hide-details
        :item-text="itemText"
        item-value="id"
        return-object
        :search-input.sync="searchFilter"
        @update:search-input="onUpdateSearchText"
        label="Поиск проекта"
        prepend-inner-icon="mdi-magnify"
        :items="filteredData"
        dense
        style="max-width: 270px"
        @input="onSearch"
      >
        <template v-slot:no-data>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>
                <span v-if="searchFilter === null || searchFilter.length === 0">
                  Поиск по проектам
                </span>
                <span v-else>
                   Нет результатов для "<strong>{{ searchFilter }}</strong>".
                </span>
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </template>
      </v-combobox>
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
            plain
            icon
            @click="onLogout"
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
import {mapState} from "vuex";

export default defineComponent({
  name: "MainContent",
  components: {DatabaseDialog, AppDrawer},
  data: () => ({
    drawerToggled: true,
    searchFilter: null,
  }),
  computed: {
    ...mapState(['projects']),
    filteredData() {
      let data = this.projects || [];
      return data.filter((el) => el.name.toLowerCase().includes(this.searchFilter?.toLowerCase()));
    },
  },
  methods: {
    itemText(item) {
      return item.surname ?  `${item.name} ${item.surname}` : item.name;
    },
    onDrawerToggle(){
      this.drawerToggled = !this.drawerToggled;
    },
    onLogout(){
      localStorage.clear();
      this.$router.push({name: 'auth'});
    },
    onSearch(v) {
      if (v && this.$route.params?.id !== v.id) this.$router.push(`/projects/${v.id}`);
    },
    onUpdateSearchText(v) {
      if (v.length === 0) this.searchFilter = null;
    }
  }
})
</script>

<style>
.app-bar_search .v-input__append-inner {
  display: none;
}
</style>
