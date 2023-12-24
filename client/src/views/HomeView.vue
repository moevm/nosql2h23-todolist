<template>
    <HomeContent/>
</template>

<script>
import HomeContent from '@/components/HomeContent.vue';
import {mapMutations} from "vuex";

export default {
  name: 'HomeView',
  components: {HomeContent},
  inject: ['projectService'],
  methods: {
    ...mapMutations(['setPersons']),
  },
  mounted() {
    this.projectService.getPersons().then((res) => {
      this.setPersons(res);
    }).catch((e) => console.error(e));
  }
}
</script>
