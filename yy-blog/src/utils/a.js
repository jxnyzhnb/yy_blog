export function setLocal (key, value){

  var endTime =  new Date(new Date(new Date().toLocaleDateString()).getTime() + 24 * 60 * 60 * 1000 - 1);
  console.log(endTime)
  const obj = {
    data: value,
    time: Date.now(),
    expire: endTime+8*60*60*1000
  };
  localStorage.setItem(key, JSON.stringify(obj));
}
export function getLocal(key)  {
  if (localStorage.getItem(key) != null) {
    var data = JSON.parse(localStorage.getItem(key));
    if (data !== null) {
      console.log(data.expire)
      if (data.expire != null && data.expire < new Date().getTime()) {
        localStorage.removeItem(key);
      } else {
        return data.data;
      }
    }
  }
  return null;
}
