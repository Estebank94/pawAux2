/**
 * Created by estebankramer on 14/10/2019.
 */
import React from 'react'
import BounceLoader from 'react-spinners/BounceLoader';

import fetchApi from '../../utils/api'

class Specialist extends React.Component {
    state = {
        loading: true,
        error: false,
        specialist: null,
    }

    componentDidMount() {
        const { id } = this.props.match.params;
        fetchApi('/doctor/' + id, 'GET')
            .then(specialist => {
                console.log(specialist);
                this.setState({loading: false, specialist });
            })
            .catch(() => this.setState({ loading: false, error: true }));
    }
    render() {
        const { error, loading, specialist } = this.state;

        if(loading) {
            return (
                <div className="centered">
                    <BounceLoader
                        sizeUnit={"px"}
                        size={75}
                        color={'rgb(37, 124, 191)'}
                        loading={true}
                    />
                </div>
            )
        }

        if(error) {
            return (
                <p>Hubo un error!</p>
            )
        }

        const { address, averageRating, district, firstName, id, insurancesPlans, lastName, phoneNumber, profilePicture,
        reviews, sex, specialties, workingHours } = specialist;

        return (
            <div className="body-background">
                <div className="main-container">



                    <div class="container">
                        <div>
                            <div class="card flex-row" style={{ marginTop: 30, marginLeft: 0, marginRight: 0 }}>
                                <div class="card-body">
                                    <div class="card-text">
                                        <div class="row">
                                            <img class="avatar big" src={`data:image/jpeg;base64,${profilePicture}`} />
                                        <div class="doctor-info-container">
                                            <div>
                                                <div class="row center-vertical">
                                                    <p>{firstName}</p>
                                                    <p>{lastName}"</p>
                                                    <h3 class="doctor-name" style={{marginLeft: 14 }}>sdfdsdfsdf</h3>
                                                    {/*<security:authorize access="isAuthenticated()">*/}
                                                        {/*<c:if test="${user.isFavorite(doctor) || isFavorite eq true}">*/}
                                                            {/*<form:form FmodelAttribute="favorite" method="POST" action="${specialist_id}" id="favorite">*/}
                                                                {/*<div class="heart-added" onclick="removeFavorite()"></div>*/}
                                                            {/*</form:form>*/}
                                                        {/*</c:if>*/}
                                                        {/*<c:if test="${user.isFavorite(doctor) eq false || isFavorite eq false}">*/}
                                                            {/*<form id="favorite">*/}
                                                                {/*<div class="heart" onclick="addFavorite()"></div>*/}
                                                            {/*</form:form>*/}
                                                        {/*</c:if>*/}
                                                    {/*</security:authorize>*/}

                                                </div>
                                                <div class="row container">
                                                    {/*<c:forEach items="${doctor.specialties}" var="doctorSpecialty">*/}
                                                        {/*<p class="doctor-specialty" style="padding-right: 2em"><c:out value="${doctorSpecialty.speciality}"/></p>*/}
                                                    {/*</c:forEach>*/}
                                                </div>
                                                <p class="doctor-text"><i class="fas fa-phone" style={{paddingRight: 0.5 }} />{phoneNumber}</p>
                                                <p class="doctor-text"><i class="fas fa-map-marker-alt" style={{paddingRight: 0.5 }} />{address}, CABA</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div>
                                    <div style={{ backgroundColor: '#F3F3F4', borderRadius: 5, padding: 16, paddingBottom: 0, marginTop: 32, marginBottom: 32 }}>
                                        <h3 class="doctor-name">specialist.reserveAppointment</h3>
                                        {/*<c:url var="specialist_id"  value="/specialist/${doctor.id}" />*/}
                                        <form id="appointment">
                                            <div class="row">
                                                <div class="col-sm-5">
                                                    <label for="day">specialist.appointmentDay"</label>
                                                    <select class="custom-select" id="day" path="day" cssStyle="cursor: pointer;">
                                                        {/*<option value="no" selected><spring:message code="appointment.choose.day"/></option>*/}
                                                        {/*<c:forEach items="${appointmentsAvailable}" var="date">*/}
                                                            {/*<option value="${date.key}" label="${date.key}"><c:out value="${date.key}"/></option>*/}
                                                        {/*</c:forEach>*/}
                                                    </select>
                                                </div>
                                                <div class="col-sm-5">
                                                    <label for="time">specialist.appointmentTime</label>
                                                    <select class="custom-select" disabled="false" id="time" cssStyle="cursor: pointer;">
                                                        <option value="no" label="Elegi el Horario" selected>appointment.choose.time</option>
                                                        {/*<c:forEach items="${appointmentsAvailable}" var="date">*/}
                                                            {/*<c:forEach items="${date.value}" var="listItem">*/}
                                                                {/*<option value="${listItem.appointmentDay}_${listItem.appointmentTime}" day="${listItem.appointmentDay}" time="${listItem.appointmentTime}"><c:out value="${listItem.appointmentTime}"/></option>*/}
                                                                {/*<%--<input type="hidden" id="time" name="time" value="${listItem.appointmentTime}">--%>*/}
                                                            {/*</c:forEach>*/}
                                                        {/*</c:forEach>*/}
                                                    </select>
                                                </div>
                                                {/*<security:authorize access="!isAuthenticated()">
                                                //     <div class="col-sm-2">
                                                //         <button type="button" class="btn btn-primary custom-btn" style="position: absolute; bottom: 0;" onclick="window.location='<c:url value="/showLogIn"/>'"><spring:message code="appointment.choose"/></button>
                                                //     </div>
                                                // </security:authorize>*/}
                                        {/*<security:authorize access="isAuthenticated()">*/}
                                            {/*<div class="col-sm-2">*/}
                                                {/*<button type="submit" class="btn btn-primary custom-btn" path="submit" style="position: absolute; bottom: 0;"><spring:message code="appointment.choose"/></button>*/}
                                            {/*</div>*/}
                                        {/*</security:authorize>*/}
                                    </div>
                                    </form>
                                </div>

                                <h3 id="information">"specialist.infoTitle"</h3>
                                    {/*<c:if test="${not empty doctor.description.certificate}">*/}
                                        {/*<h4><spring:message code="specialist.certificate" /></h4>*/}
                                        {/*<c:forEach items="${doctor.description.certificate}" var="certificate">*/}
                                            {/*<c:out value="${certificate}"/>*/}
                                        {/*</c:forEach>*/}
                                    {/*</c:if>*/}
                                    {/*<c:if test="${not empty doctor.description.education}">*/}
                                        <h4>"specialist.education"</h4>
                                        {/*<c:forEach items="${doctor.description.education}" var="education">*/}
                                            {/*<c:out value="${education}"/>*/}
                                        {/*</c:forEach>*/}
                                        {/*<br/>*/}
                                            {/*<br/>*/}
                                    {/*</c:if>*/}
                                    <h4>"specialist.insurances"</h4>
                                    {/*<div class="row container">*/}
                                        {/*<c:forEach items="${doctor.insuranceListFromInsurancePlans}" var="insurance">*/}
                                            {/*<div class="card col-sm-3" style="margin-right:2rem; padding-left: 0; padding-right: 0; margin-top:8px;">*/}
                                                {/*<div class="card-header">*/}
                                                    {/*<strong><c:out value="${insurance.name}"/></strong>*/}
                                                {/*</div>*/}
                                                {/*<ul class="list-group list-group-flush">*/}
                                                    {/*<c:forEach items="${doctor.getInsurancePlansFromInsurance(insurance)}" var="plan">*/}
                                                        {/*<li class="list-group-item" style="max-height: 24rem"><c:out value="${plan.plan}"/></li>*/}
                                                    {/*</c:forEach>*/}
                                                {/*</ul>*/}
                                            {/*</div>*/}
                                        {/*</c:forEach>*/}
                                    {/*</div>*/}
                                {/*<c:if test="${doctor.description.languages.length() > 0 && languagesNo eq false}">*/}
                                    <h4>"specialist.languages"</h4>
                                    {/*<c:forEach items="${doctor.description.languages}" var="languages">*/}
                                        {/*<c:out value="${languages}"/>*/}
                                    {/*</c:forEach>*/}
                                {/*</c:if>*/}
                            </div>
                            <hr />
                                {/*<c:if test="${doctor.reviews.size() > 0}">*/}
                                    {/*<div>*/}
                                        {/*<h4><spring:message code="panel.review"/></h4>*/}
                                            {/*<c:forEach items="${doctor.reviews}" var="review">*/}
                                                {/*<p style="margin-bottom: 4px"><strong><c:out value="${review.reviewerFirstName} ${review.reviewerLastName}"/></strong></p>*/}
                                                {/*<div class="container row">*/}
                                                    {/*<c:forEach begin = "1" end = "${review.stars}">*/}
                                                        {/*<i class="fas fa-star star-yellow star-small"></i>*/}
                                                    {/*</c:forEach>*/}
                                                {/*</div>*/}
                                                {/*<p><c:out value="${review.description}"/></p>*/}
                                                {/*<hr style="margin-top:8px; margin-bottom: 8px;">*/}
                                            {/*</c:forEach>*/}
                                    {/*</div>*/}
                                {/*</c:if>*/}
                                {/*<security:authorize access="isAuthenticated()">*/}
                                    {/*<div>*/}
                                        {/*<h4><spring:message code="panel.leave.review"/></h4>*/}
                                        {/*<br>*/}
                                            {/*<c:url var="specialist_id"  value="/specialist/${doctor.id}" />*/}
                                            {/*<form:form modelAttribute="review" method="POST" action="${specialist_id}" id="review">*/}
                                                {/*<div class="form-group">*/}
                                                    {/*<label for="stars"><spring:message code="panel.review.stars"/></label>*/}
                                                    {/*<form:select class="form-control" id="stars" path="stars">*/}
                                                        {/*<option><spring:message code="one"/></option>*/}
                                                        {/*<option><spring:message code="two"/></option>*/}
                                                        {/*<option><spring:message code="three"/></option>*/}
                                                        {/*<option><spring:message code="four"/></option>*/}
                                                        {/*<option><spring:message code="five"/></option>*/}
                                                    {/*</form:select>*/}
                                                {/*</div>*/}
                                                {/*<div class="form-group">*/}
                                                    {/*<label for="description"><spring:message code="panel.review.experience"/></label>*/}
                                                    {/*<form:textarea class="form-control" id="description" path="description" rows="3"/>*/}
                                                {/*</div>*/}
                                                {/*<button type="submit" class="btn btn-primary custom-btn mb-2"><spring:message code="panel.review.send"/></button>*/}

                                            {/*</form:form>*/}
                                    {/*</div>*/}
                                {/*</security:authorize>*/}
                                </div>
                            </div>
                        </div>
                    </div>





                </div>
            </div>
        )
    }
}

export default Specialist;