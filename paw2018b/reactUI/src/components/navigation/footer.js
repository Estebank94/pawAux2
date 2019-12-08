import React from 'react'

class Footer extends React.Component {
  render() {
    const { pathname } = this.props.location;
    return(
      <div style={{ backgroundColor: '#F3F3F4' }}>
        <div class="container">
          <p class="footer-text">© Copyright 2019. Waldoc</p>
        </div>
      </div>
    );
  }
}

export default Footer;

