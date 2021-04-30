import { reactive, Ref, toRefs, provide } from '@nuxtjs/composition-api'
import axios from 'axios';

export interface Row {
    id: number
    name: string
    note: string
}

export interface Records {
    rows: Row[]
}

export interface RecordsState {
  records: Ref<Records>
  load: (rs: Records) => Promise<Records>
  fetchRec: () => Promise<Records>
}


const createState = (): (() => RecordsState) => {
  /*
  const state = reactive({
    records: { rows: [
      {id: 1, name: 'test001', note:'xxxx'},
      {id: 2, name: 'test002', note:'xxxx'},
    ]}
  })

  const load = async (rs: Records): Promise<Records> => {
    return new Promise<Records>(resolve => {
      setTimeout(() => {
        Object.assign(state.records, rs)
        resolve(rs)
      }, 1000)
    })
  }
  const fetchRec = async (): Promise<Records> => {
    const response = await axios.get('/rec.json')
    const rec = {rows: response.data} as Records
    Object.assign(state.records, rec)
    return rec
  }
  */

  const factory = (): RecordsState => {

  const state = reactive({
    records: { rows: [
      {id: 1, name: 'test001', note:'xxxx'},
      {id: 2, name: 'test002', note:'xxxx'},
    ]}
  })

  const load = async (rs: Records): Promise<Records> => {
    return new Promise<Records>(resolve => {
      setTimeout(() => {
        Object.assign(state.records, rs)
        resolve(rs)
      }, 1000)
    })
  }
  const fetchRec = async (): Promise<Records> => {
    const response = await axios.get('/rec.json')
    const rec = {rows: response.data} as Records
    Object.assign(state.records, rec)
    return rec
  }




    return {
      ...toRefs(state),
      load,
      fetchRec,
    }
  }

  /*
  const records = { rows: [
    {id: 1, name: 'test001', note:'xxxx'},
    {id: 2, name: 'test002', note:'xxxx'},
    {id: 3, name: 'test003', note:'xxxx'},
  ]} as Records
  */

  return factory
}

export const useState = createState()
