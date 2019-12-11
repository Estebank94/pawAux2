export const isValidEmail = (email) => {
  const validEmailRegex =
    RegExp(/^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i);
  console.log(validEmailRegex.test(email));
  return validEmailRegex.test(email)
}

export const isValidPhone = (phone) => {
  // const validPhoneRegex =
    // RegExp(/^(?:(?:00)?549?)?0?(?:11|[2368]\d)(?:(?=\d{0,2}15)\d{2})??\d{8}$/g);
  // console.log('phone valid',validPhoneRegex.test(phone));
  // return validPhoneRegex.test(phone)
  return phone.length > 5
}
