<template>
  <v-item-group
    mandatory
  >
    <v-item
      v-for="(filter, index) in filters"
      :key="index"
    >
      <v-btn
        class="me-2"
        small
        rounded
        :color="currentFilter.name === filter.name ? 'primary' : ''"
        @click="setFilter(filter)"
      >
        {{ filter.text }}
      </v-btn>
    </v-item>
  </v-item-group>
</template>

<script>
import {mapMutations, mapState} from "vuex";

export default {
  name: "FiltersPanel",
  computed: mapState([
    'filters', 'currentFilter',
  ]),
  methods: {
    ...mapMutations([
      'setFilter'
    ]),
  }
}
</script>

<style scoped>

</style>