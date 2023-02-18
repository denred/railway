<%@ attribute name="currentPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="numPages" required="true" type="java.lang.Integer" %>
<%@ attribute name="lastPage" required="true" type="java.lang.Integer" %>
<%@ attribute name="url" required="true" type="java.lang.String" %>

<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<%-- Pagination --%>
<div class="d-flex justify-content-center">
    <nav aria-label="Page navigation">
        <ul class="pagination ">
            <li class="page-item">
                <c:if test="${currentPage != 1}">
                    <a class="page-link" href="${url}&page=${currentPage - 1}"
                       aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>
                </c:if>
            </li>

            <c:forEach begin="1" end="${numPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active" aria-current="page">
                            <a class="page-link" href="#">${i}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="${url}&page=${i}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>


            <c:if test="${lastPage gt numPages}">
                <li class="page-item disabled">
                    <a class="page-link circle circle-md" href="#"><span>...</span></a>
                </li>
                <c:choose>
                    <c:when test="${currentPage eq lastPage}">
                        <li class="page-item active">
                            <a class="page-link"
                               href="${url}&page=${lastPage}">${lastPage}</a>
                        </li>
                    </c:when>

                    <c:otherwise>
                        <c:if test="${currentPage gt numPages}">
                            <li class="page-item active">
                                <a class="page-link"
                                   href="${url}&page=${currentPage}">${currentPage}</a>
                            </li>
                            <li class="page-item disabled">
                                <a class="page-link circle circle-md"
                                   href="#"><span>...</span></a></li>
                        </c:if>
                        <li class="page-item">
                            <a class="page-link"
                               href="${url}&page=${lastPage}">${lastPage}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:if>

            <c:if test="${currentPage lt lastPage}">
                <li class="page-item">
                    <a class="page-link" href="${url}&page=${currentPage + 1}"
                       aria-label="Next">
                        <span aria-hidden="true">&raquo;</span></a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>