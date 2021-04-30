<template>
  <div>
    <Table />
    <button @click="fetchRec()" >API</button>
  </div>
</template>
<script lang="ts">
import { defineComponent, onMounted } from '@nuxtjs/composition-api'

import { useState, Records } from '~/states'

export default defineComponent({
  setup() {
    const { fetchRec, load } = useState()
    onMounted(() => {
      load({rows:[]} as Records)
    })
    return {
      fetchRec,
    }
  },
})
</script>
