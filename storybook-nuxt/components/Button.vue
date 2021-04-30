<template>
  <button @click="$emit('click')"><slot/></button>
</template>
<script lang="ts">
import { defineComponent } from '@nuxtjs/composition-api'

export default defineComponent({
  emits: ['click'],
})
</script>
