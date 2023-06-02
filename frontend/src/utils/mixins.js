import axios from 'axios';
import Store from '@/store/index';
const token = Store.state.token;
axios.defaults.baseURL = 'http://localhost:8090/';
axios.defaults.headers.common.Authorization = `Bearer ${token}`;

axios.interceptors.response.use(response => response, error => {
  const status = error.response.status;
  if (status === 401) {
    // 엑세스 토큰이 만료되었을때
    return refreshTokenAndRetryRequest(error.config);
  }
  const promise = new Promise.reject(error);
  return promise;
});

const refreshTokenAndRetryRequest = async (config) => {
  try {
    // 토큰 갱신
    const response = await axios.post('refreshToken');
    const newToken = response.headers.accessToken;
    Store.commit('setToken', newToken);
    // 갱신 토큰 저장
    axios.defaults.headers.common.Authorization = `Bearer ${newToken}`;
    return axios(config);
  } catch (e) {
    const promise = new Promise().reject(e);
    return promise;
  }

}
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