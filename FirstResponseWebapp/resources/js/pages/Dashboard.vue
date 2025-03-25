<template>
    <Index />
</template>
<script>
import { defineComponent } from 'vue';
import Index from '@/components/Dashboard/index.vue';

export default defineComponent({
  name: 'Dashboard',

  components: {
    Index,
  },
});
</script>
