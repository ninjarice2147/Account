class MyHeader extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
        <header class="header">
          <!-- 清單背景 -->
          <div class="block">
            <!-- logo圖片 -->
            <nav class="nav">
              <a href="./home_page.html" class="logo"><img src="../images/logo.png" ></a>
              <ul class="ul_list">
                <!-- 各種分類 -->
                <li><a href="./home_page.html">主頁</a></li>
                <li><a href="../../index.jsp">現金流系統</a></li>
                <li><a href="./Undecided_1.html">未定1</a></li>
                <li><a href="./Undecided_2.html">未定2</a></li>
              </ul>
              <!--社群連結-->
              <div class="share_block">
                <span class="share_text">社群連結</span>
                <ul class="share_list">
                  <li><a href="https://www.facebook.com/?locale=zh_TW">FB</a></li>
                  <li><a href="https://twitter.com/home">twitter</a></li>
                </ul>
              </div>
            </nav>
          </div>
        </header>
        `;
    }
}

// 註冊標籤名稱為 <my-header>
customElements.define('my-header', MyHeader);


class MyFooter extends HTMLElement {
  connectedCallback() {
    this.innerHTML = `
      <footer class="footer">
        范姜奕 自我介紹作品
        <span class="text">前端使用編輯器Atom</span>
      </footer>
    `;
  }
}
customElements.define('my-footer', MyFooter);
