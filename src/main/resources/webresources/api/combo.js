// 查询列表数据
const getSetmealPage = (params) => {
  return $axios({
    url: '/setmeal/getSetmealPage',
    method: 'get',
    params
  })
}

// 删除数据接口
const deleteSetmeal = (ids) => {
  return $axios({
    url: '/setmeal/deleteSetmeals',
    method: 'delete',
    params: { ids }
  })
}

// 修改数据接口
const editSetmeal = (params) => {
  return $axios({
    url: '/setmeal',
    method: 'put',
    data: { ...params }
  })
}

// 新增数据接口
const addSetmeal = (params) => {
  return $axios({
    url: '/setmeal/addSetmeal',
    method: 'post',
    data: { ...params }
  })
}

// 查询详情接口
const querySetmealById = (id) => {
  return $axios({
    url: `/setmeal/${id}`,
    method: 'get'
  })
}

// 批量起售禁售
const setmealStatusByStatus = (params) => {
  return $axios({
    url: `/setmeal/changeStatus/${params.status}`,
    method: 'put',
    params: { ids: params.ids }
  })
}
