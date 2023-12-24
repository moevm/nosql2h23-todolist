<template>
  <v-dialog
      v-model="dialog"
      persistent
      max-width="400"
  >
    <template v-slot:activator="{ on: onDialog, attrs: dialogAttrs }">
      <v-btn
          v-bind="dialogAttrs"
          v-on="onDialog"
          icon
          plain
          large
      >
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-icon v-bind="attrs" v-on="on">mdi-database-outline</v-icon>
          </template>
          <span>Импорт/экспорт ДБ</span>
        </v-tooltip>
      </v-btn>
    </template>
    <v-toolbar
        flat
        dense
    >
      <v-tabs
          v-model="tab"
          fixed-tabs
      >
        <v-tabs-slider/>

        <v-tab
            v-for="item in items"
            :key="item.label"
        >
          <v-icon class="mr-2">{{item.icon}}</v-icon>
          {{ item.label }}
        </v-tab>
      </v-tabs>
    </v-toolbar>

    <v-tabs-items v-model="tab">
      <v-tab-item>
        <v-card flat>
          <v-card-text>
            <v-file-input
              v-model="file"
              label="Выберите JSON файл"
              accept=".json"
            ></v-file-input>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                text
                @click="dialog = false"
            >
              Отменить
            </v-btn>
            <v-btn
                text
                :disabled="!file"
                @click="onExport"
            >
              Экспорт
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                text
                @click="dialog = false"
            >
              Отмена
            </v-btn>
            <v-btn
                text
                @click="onImport"
            >
              Импорт
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </v-dialog>
</template>

<script>
import {defineComponent} from 'vue'
import {mapActions} from "vuex";

export default defineComponent({
  name: "DatabaseDialog",
  inject: ['projectService'],
  data: () => ({
    selected: [],
    dialog: false,
    tab: null,
    file: null,
    items: [{
      label: 'Экспорт',
      icon: 'mdi-database-import-outline',
    }, {
      label: 'Импорт',
      icon: 'mdi-database-export-outline',
    }],
    text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
  }),
  methods: {
    ...mapActions(['updateProjects']),
    onImport() {
      this.projectService.importDb().catch((e) => console.error(e));
    },
    onExport() {
      const reader = new FileReader(); // File reader to read the file

      // This event listener will happen when the reader has read the file
      reader.onloadend = (evt) => {
        if (evt.target.readyState == FileReader.DONE) {
          const result = JSON.parse(reader.result); // Parse the result into an object
          this.projectService.exportDb(result).then(() => {
            this.updateProjects();
            if (this.$route.path !== '/') this.$router.push('/');
          }).catch((e) => console.error(e));
          this.dialog = false;
        }
      };
      reader.readAsText(this.file);
    },
  },
})
</script>

<style scoped>

</style>
