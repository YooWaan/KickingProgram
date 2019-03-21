<template>
	<div>
		<div>
			<input v-model.trim="name"></input>
		</div>
		<div>
			<p :show="showText">{{name}}</p>
		</div>
	</div>
</template>

<script>
export default {
  data : {
    name: ''
  },
  computed: {
    showText() {
      return this.name.length > 4 ? true : false;
    }
  }
}
</script>
