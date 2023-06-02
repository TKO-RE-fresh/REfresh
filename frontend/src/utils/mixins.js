import axios from 'axios';
axios.defaults.baseURL = 'http://localhost:8090/';
export default {
  methods: {
    async $api(url, method, { body, params }) {
      return (await axios({
        method,
        url,
        body,
        params
      }).catch(e => console.log(e)))
    }
  }
}