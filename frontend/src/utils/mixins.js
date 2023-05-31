import axios from 'axios';
axios.defaults.baseURL = 'http://localhost:8090/';
export default {
  methods: {
    async $api(url, method, { data, params }) {
      return (await axios({
        method,
        url,
        data,
        params
      }).catch(e => console.log(e)))
    }
  }
}