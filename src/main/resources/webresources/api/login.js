function loginApi(phone,pwd) {
  return $axios({
    url: '/employee/login',
    method: 'post',
    data:{
      phone:phone,
      password:pwd
    }
  })
}

function logoutApi(){
  return $axios({
    'url': '/employee/logout',
    'method': 'post'
  })
}
