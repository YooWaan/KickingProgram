//import axios from 'axios'
//import sinon from 'sinon'

//import Pane from '~/components/Pane.vue'
import Table from '~/components/Table.vue'

//sinon.stub(axios, 'get').resolves(Promise.resolve([{id:10, name:'mock', note: 'note....'}]))

export default {
  component: Table,
  title: 'Table',
}
export const Rows = () => ({
  components: { Table },
  template: '<Table />',
})
