export const isValidEmail = (email) => {
  const validEmailRegex =
    RegExp(/^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i);
  return validEmailRegex.test(email)
}

export const isValidLetters = (text) => {
  const objRegExp  = /^[a-z\u00C0-\u00ff]+$/;
  return objRegExp.test(text);
}