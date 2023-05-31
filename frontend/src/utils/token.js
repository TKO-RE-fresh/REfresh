import Cookies from 'js-cookie';
export default {
  getToken() {
    return Cookies.get('token')
  },
  setToken(token) {
    Cookies.set('token', token, { httpOnly: true, secure: true, sameSite: 'strict' })
  },
  removeToken() {
    Cookies.remove('token')
  }
}