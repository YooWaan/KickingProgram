import Button from '../../components/Button.vue'

export default {
  component: Button,
  title: 'Button',
}


export const Primary = () => ({
  components: { Button },
  template: '<Button label="Button" >Test</Button>',
});
//export const NuxtWebsite = () => '<Button>Hi !!</Button>'
