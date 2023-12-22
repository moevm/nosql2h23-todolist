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
                chips
                multiple
                label="Выберите файл"
            ></v-file-input>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                text
                @click="dialog = false"
            >
              Cancel
            </v-btn>
            <v-btn
                text
                @click="dialog = false"
            >
              Confirm
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-card-text>
            <v-checkbox
                v-model="selected"
                label="Фильтр 1"
                value="filter1"
                hide-details
            />
            <v-checkbox
                v-model="selected"
                label="Фильтр 2"
                value="filter2"
                hide-details
            />
            <v-checkbox
                v-model="selected"
                label="Фильтр 3"
                value="filter3"
                hide-details
            />
            <v-checkbox
                v-model="selected"
                label="Фильтр 4"
                value="filter4"
                hide-details
            />
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                text
                @click="dialog = false"
            >
              Cancel
            </v-btn>
            <v-btn
                text
                @click="dialog = false"
            >
              Confirm
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </v-dialog>
</template>

<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "DatabaseDialog",
  data: () => ({
    selected: [],
    dialog: false,
    tab: null,
    items: [{
      label: 'Import',
      icon: 'mdi-database-import-outline',
    }, {
      label: 'Export',
      icon: 'mdi-database-export-outline',
    }],
    text: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
  }),
})
</script>

<style scoped>

</style>
