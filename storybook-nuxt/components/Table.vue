<template>
  <table>
    <thead>
      <th>id</th>
      <th>name</th>
      <th>note</th>
    </thead>
    <tbody>
      <tr v-for="row in records.rows" :key="row.id">
        <td>{{ row.id  }}</td>
        <td>{{ row.name }}</td>
        <td>{{ row.note  }}</td>
      </tr>
    </tbody>
  </table>
</template>

<script lang="ts">
import { defineComponent } from '@nuxtjs/composition-api'

import { useState } from '~/states'

export default defineComponent({
  setup() {
    const { records } = useState()

    return {
      records,
    }
  },
})
</script>
