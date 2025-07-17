<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />    

	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/css/footer.css" />
	<script src="https://kit.fontawesome.com/91fb88d05c.js" crossorigin="anonymous"></script>
    <link href="https://cdn.lineicons.com/5.0/lineicons.css" rel="stylesheet" />
    <!-- footer -->
    <footer class="footer">
        <div class="footer-container">
            <div class="footer-column">
                <div class="footer-socials">
                    <a href="#"><i class="lni lni-facebook"></i></a>
                    <a href="#"><i class="lni lni-instagram"></i></a>
                    <a href="#"><i class="lni lni-discord"></i></a>
                    <a href="#"><i class="lni lni-x"></i></a>
                    <a href="#"><i class="lni lni-tiktok-alt"></i></a>
                </div>
            </div>
            <!-- Company -->
            <div class="footer-column">
                <h3>COMPANY</h3>
                <ul>
                    <li><a href="${contextPath}/aboutUs">About us</a></li>
                    <li><a href="${contextPath}/terms">Terms and Conditions</a></li>
                    <li><a href="${contextPath}/privacy">Privacy Policy</a></li>
                </ul>
            </div>

            <!-- Customer Service -->
            <div class="footer-column">
                <h3>Customer Service</h3>
                <ul>
                    <li><a href="${contextPath}/return">Return Policy</a></li>
                    <li><a href="${contextPath}/warranty">Warranty Policy</a></li>
                    <li><a href="${contextPath}/contact">Contact Us</a></li>
                </ul>
            </div>

            <!-- Payment Options -->
            <div class="footer-column">
                <h3>Payment Options</h3>
                <ul class="payment">
                    <li><img src="${contextPath}/resources/payments/esewa.png" alt="Esewa" /> Esewa</li>
                    <li><img src="${contextPath}/resources/payments/imepay.png" alt="Khalti"> ImePay</li>
                    <li><img src="${contextPath}/resources/payments/khalti.png" alt="ImePay"> Khalti</li>
                </ul>
            </div>
        </div>

        <div class="footer-bottom">
            <p><b>Copyright © 2025 Onez Pvt. Ltd. All right reserved.</b></p>
        </div>
    </footer>
    <!-- footer containers ends -->
