const demoData = {
  companies: [
    { id: 1, name: "TCS", location: "Kolkata" },
    { id: 2, name: "Infosys", location: "Bengaluru" },
    { id: 3, name: "Deloitte", location: "Hyderabad" },
    { id: 4, name: "Cognizant", location: "Chennai" }
  ],
  jobs: [
    { id: 1, role: "Software Engineer Trainee", salary: 6.5, minCgpa: 7.0, lastDate: "18-05-2026", company: { name: "TCS", location: "Kolkata" } },
    { id: 2, role: "Java Backend Intern", salary: 4.8, minCgpa: 7.5, lastDate: "24-05-2026", company: { name: "Infosys", location: "Bengaluru" } },
    { id: 3, role: "Analyst Trainee", salary: 7.2, minCgpa: 8.0, lastDate: "30-05-2026", company: { name: "Deloitte", location: "Hyderabad" } }
  ],
  announcements: [
    { id: 1, info: "Resume verification closes tomorrow at 4 PM. Upload the latest resume link in your profile.", publishTime: "2026-05-04T10:30:00" },
    { id: 2, info: "Mock aptitude assessment for all CSE and IT students is scheduled this Friday.", publishTime: "2026-05-03T14:00:00" }
  ],
  applications: [
    { id: 1, studentName: "Pratham Kumar", companyName: "Deloitte", rollNo: "TINT001", cgpa: 8.4, activeBackLog: "No" },
    { id: 2, studentName: "Ananya Sen", companyName: "TCS", rollNo: "TINT002", cgpa: 8.1, activeBackLog: "No" }
  ],
  students: [
    { id: 1, branch: "CSE", cgpa: 8.7, resumeUrl: "https://example.com/resume" },
    { id: 2, branch: "IT", cgpa: 8.1, resumeUrl: "https://example.com/resume" },
    { id: 3, branch: "ECE", cgpa: 7.6, resumeUrl: "https://example.com/resume" }
  ]
};

const state = {
  session: JSON.parse(localStorage.getItem("placementSession") || "null"),
  profile: JSON.parse(localStorage.getItem("placementProfile") || "null"),
  jobSort: localStorage.getItem("placementJobSort") || "recent",
  applicationCompanyFilter: localStorage.getItem("placementApplicationCompanyFilter") || "",
  companies: [],
  jobs: [],
  announcements: [],
  applications: [],
  students: []
};

if (state.session && !state.session.token) {
  state.session = null;
  localStorage.removeItem("placementSession");
}

const $ = (selector) => document.querySelector(selector);
const $$ = (selector) => Array.from(document.querySelectorAll(selector));

function authHeaders() {
  if (!state.session?.token) return {};
  const type = state.session.type || "Bearer";
  return { Authorization: `${type} ${state.session.token}` };
}

async function api(path, options = {}) {
  const response = await fetch(path, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...authHeaders(),
      ...(options.headers || {})
    }
  });

  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || `Request failed with ${response.status}`);
  }

  const contentType = response.headers.get("content-type") || "";
  return contentType.includes("application/json") ? response.json() : response.text();
}

function roleFromAuthorities(roles = []) {
  return roles.some((role) => String(role).toUpperCase() === "ROLE_ADMIN") ? "admin" : "student";
}

function createSession(username, authResponse) {
  return {
    username,
    token: authResponse.token,
    type: authResponse.type || "Bearer",
    roles: Array.isArray(authResponse.roles) ? authResponse.roles : [],
    role: roleFromAuthorities(authResponse.roles),
    expireTime: authResponse.expireTime || authResponse.expieTime || null
  };
}

async function login(username, password) {
  const authResponse = await api("/api/auth/login", {
    method: "POST",
    body: JSON.stringify({ userName: username, password })
  });
  return createSession(username, authResponse);
}

async function loadCollection(path, fallback) {
  try {
    return await api(path);
  } catch (error) {
    return fallback;
  }
}

async function refreshData() {
  const jobsEndpoint = jobEndpoint();
  const announcementsEndpoint = state.session?.role === "admin" ? "/api/admin/get-all-announcement" : "/api/student/get-all-announcement";
  const applicationsRequest = state.session?.role === "admin"
    ? loadCollection(applicationEndpoint(), filterApplicationsByCompany(demoData.applications, state.applicationCompanyFilter))
    : Promise.resolve([]);

  const [companies, jobs, announcements, applications] = await Promise.all([
    loadCollection("/api/company", demoData.companies),
    loadCollection(jobsEndpoint, sortJobs(demoData.jobs, state.jobSort)),
    loadCollection(announcementsEndpoint, demoData.announcements),
    applicationsRequest
  ]);

  state.companies = Array.isArray(companies) ? companies : demoData.companies;
  state.jobs = Array.isArray(jobs) ? jobs : sortJobs(demoData.jobs, state.jobSort);
  state.announcements = Array.isArray(announcements) ? announcements : demoData.announcements;
  state.applications = Array.isArray(applications) ? applications : [];
  state.students = demoData.students;
  render();
}

function jobEndpoint() {
  if (state.jobSort === "recent") return "/api/student/jobs/recent";
  if (state.jobSort === "oldest") return "/api/student/jobs/oldest";
  return state.session?.role === "admin" ? "/api/admin/job" : "/api/student/jobs";
}

function applicationEndpoint() {
  const companyName = state.applicationCompanyFilter.trim();
  if (!companyName) return "/api/admin/get-all-application";
  return `/api/admin/get-application?companyName=${encodeURIComponent(companyName)}`;
}

function filterApplicationsByCompany(applications, companyName) {
  const query = companyName.trim().toLowerCase();
  if (!query) return applications;
  return applications.filter((application) => String(application.companyName || "").toLowerCase().includes(query));
}

function sortJobs(jobs, sort) {
  const sorted = [...jobs];
  if (sort === "recent") {
    return sorted.sort((a, b) => Number(b.id || 0) - Number(a.id || 0));
  }
  if (sort === "oldest") {
    return sorted.sort((a, b) => Number(a.id || 0) - Number(b.id || 0));
  }
  return sorted;
}

function formatCurrency(value) {
  const number = Number(value || 0);
  return `${number.toFixed(number % 1 ? 1 : 0)} LPA`;
}

function parseDate(value) {
  if (!value) return null;
  if (/^\d{2}-\d{2}-\d{4}$/.test(value)) {
    const [day, month, year] = value.split("-");
    return new Date(`${year}-${month}-${day}T00:00:00`);
  }
  return new Date(value);
}

function formatDate(value) {
  const date = parseDate(value);
  if (!date || Number.isNaN(date.getTime())) return "Date pending";
  return date.toLocaleDateString("en-IN", { day: "2-digit", month: "short", year: "numeric" });
}

function toBackendDate(value) {
  const [year, month, day] = value.split("-");
  return `${day}-${month}-${year}`;
}

function companyName(job) {
  return job.company?.name || job.companyName || "Company";
}

function filterItems(items) {
  const query = $("#searchInput").value.trim().toLowerCase();
  if (!query) return items;
  return items.filter((item) => JSON.stringify(item).toLowerCase().includes(query));
}

function render() {
  const companies = filterItems(state.companies);
  const jobs = filterItems(state.jobs);
  const announcements = filterItems(state.announcements);
  const applications = filterItems(state.applications);
  const activeJobs = state.jobs.filter((job) => {
    const date = parseDate(job.lastDate);
    return !date || date >= new Date(new Date().toDateString());
  });
  const topSalary = state.jobs.reduce((max, job) => Math.max(max, Number(job.salary || 0)), 0);

  $("#companyCount").textContent = state.companies.length;
  $("#driveCount").textContent = activeJobs.length;
  $("#topPackage").textContent = formatCurrency(topSalary);
  $("#announcementCount").textContent = state.announcements.length;
  $("#applicationCount").textContent = state.applications.length;
  $("#heroCompanies").textContent = state.companies.length;
  $("#heroDrives").textContent = activeJobs.length;

  renderJobs(jobs);
  renderCompanies(companies);
  renderAnnouncements(announcements);
  renderApplications(applications);
  renderUpcoming(activeJobs);
  renderSession();
  renderRoleControls();
  renderProfile();
}

function renderJobs(jobs) {
  const container = $("#jobsGrid");
  if (!jobs.length) {
    container.innerHTML = `<div class="empty">No drives found. Add a job from the admin panel.</div>`;
    return;
  }

  container.innerHTML = jobs.map((job) => `
    <article class="data-card">
      <span class="chip good">${companyName(job)}</span>
      <h3>${job.role || "Placement role"}</h3>
      <small>${job.company?.location || "Campus drive"} placement opportunity</small>
      <div class="meta-line">
        <span class="chip">${formatCurrency(job.salary)}</span>
        <span class="chip warn">CGPA ${job.minCgpa || "0"}+</span>
        <span class="chip hot">Apply by ${formatDate(job.lastDate)}</span>
      </div>
      ${state.session?.role === "admin" ? "" : `<button class="primary full apply-btn" data-apply-company="${encodeURIComponent(companyName(job))}" type="button">Apply</button>`}
    </article>
  `).join("");
}

function renderCompanies(companies) {
  const container = $("#companiesGrid");
  if (!companies.length) {
    container.innerHTML = `<div class="empty">No companies found. Add one to start creating drives.</div>`;
    return;
  }

  container.innerHTML = companies.map((company) => `
    <article class="data-card">
      <span class="chip">Recruiter #${company.id || "--"}</span>
      <h3>${company.name || "Company"}</h3>
      <small>${company.location || "Location not added"}</small>
      <div class="meta-line">
        <span class="chip good">${state.jobs.filter((job) => companyName(job).toLowerCase() === String(company.name).toLowerCase()).length} drives</span>
      </div>
    </article>
  `).join("");
}

function renderAnnouncements(announcements) {
  const container = $("#announcementsList");
  if (!announcements.length) {
    container.innerHTML = `<div class="empty">No announcements yet.</div>`;
    return;
  }

  container.innerHTML = announcements.map((announcement) => `
    <article class="timeline-item">
      <small class="announcement-date">
        ${formatDate(announcement.publishTime)}
      </small>

      <strong>
        ${announcement.info || "Placement update"}
      </strong>
    </article>
  `).join("");
}

function renderApplications(applications) {
  const container = $("#applicationsGrid");
  $("#applicationCompanyFilter").value = state.applicationCompanyFilter;
  if (state.session?.role !== "admin") {
    container.innerHTML = `<div class="empty">Sign in as admin to review student applications.</div>`;
    return;
  }
  if (!applications.length) {
    container.innerHTML = `<div class="empty">No applications submitted yet.</div>`;
    return;
  }

  container.innerHTML = applications.map((application) => `
    <article class="data-card application-card">
      <span class="chip good">${application.companyName || "Company"}</span>
      <h3>${application.studentName || "Student"}</h3>
      <small>Roll no: ${application.rollNo || "--"}</small>
      <div class="meta-line">
        <span class="chip">CGPA ${application.cgpa || "0"}</span>
        <span class="chip ${String(application.activeBackLog).toLowerCase() === "yes" ? "hot" : "good"}">Backlog ${application.activeBackLog || "No"}</span>
      </div>
    </article>
  `).join("");
}

function renderUpcoming(activeJobs) {
  const list = $("#upcomingList");
  const upcoming = [...activeJobs]
    .sort((a, b) => (parseDate(a.lastDate) || 0) - (parseDate(b.lastDate) || 0))
    .slice(0, 4);

  if (!upcoming.length) {
    list.innerHTML = `<div class="empty">No active drives available right now.</div>`;
    return;
  }

  list.innerHTML = upcoming.map((job) => `
    <div class="list-row">
      <div>
        <strong>${job.role}</strong>
        <span>${companyName(job)} - CGPA ${job.minCgpa}+</span>
      </div>
      <span class="chip">${formatDate(job.lastDate)}</span>
    </div>
  `).join("");
}

function renderSession() {
  const name = state.session?.username || "Guest mode";
  $("#sessionName").textContent = name;
  $("#sessionHint").textContent = state.session
    ? `${state.session.role.toUpperCase()} dashboard connected with JWT`
    : "Demo data is shown until you sign in.";
}

function renderRoleControls() {
  const isAdmin = state.session?.role === "admin";
  $$(".admin-only").forEach((element) => element.classList.toggle("is-hidden", !isAdmin));
  if (!isAdmin && window.location.hash === "#applications") {
    setView("overview");
  }
}

function renderProfile() {
  if (!state.profile) return;
  $("#profileName").textContent = state.session?.username || "Student profile";
  $("#profileDetails").textContent = `${state.profile.branch} - CGPA ${state.profile.cgpa} - Resume ${state.profile.resumeUrl ? "linked" : "pending"}`;
}

function showToast(message) {
  const toast = $("#toast");
  toast.textContent = message;
  toast.classList.add("show");
  window.clearTimeout(showToast.timer);
  showToast.timer = window.setTimeout(() => toast.classList.remove("show"), 3200);
}

function setView(viewId) {
  const nextViewId = document.getElementById(viewId)?.classList.contains("view") ? viewId : "overview";
  $$(".view").forEach((view) => view.classList.toggle("active-view", view.id === nextViewId));
  $$(".nav-item").forEach((item) => item.classList.toggle("active", item.dataset.view === nextViewId));
  const title = document.querySelector(`#${nextViewId}-title`)?.textContent || "Placement Management Dashboard";
  $("#pageTitle").textContent = title;
  window.location.hash = nextViewId;
}

function openModal(id, focusTarget) {
  const modal = $(`#${id}`);
  if (modal?.showModal) {
    if (id === "authModal") {
      switchAuthPanel(focusTarget || "login");
    }
    modal.showModal();
    if (focusTarget === "register") {
      $("#registerName")?.focus();
    } else if (focusTarget === "login") {
      $("#loginUsername")?.focus();
    }
  }
}

function openApplyModal(company) {
  if (!state.session) {
    showToast("Please sign in as student before applying");
    openModal("authModal", "login");
    return;
  }
  if (state.session.role === "admin") {
    showToast("Admins can review applications from the Applications page");
    setView("applications");
    return;
  }
  $("#applyCompanyName").value = company;
  openModal("applyModal");
}

function switchAuthPanel(panelName) {
  const nextPanel = panelName === "register" ? "register" : "login";
  $$("[data-auth-panel]").forEach((panel) => {
    panel.classList.toggle("active-auth-panel", panel.dataset.authPanel === nextPanel);
  });
}

function closeOpenModal(element) {
  element.closest("dialog")?.close();
}

function formValues(form) {
  return Object.fromEntries(new FormData(form).entries());
}

async function handleFormSubmit(form, path, successMessage, transform = (data) => data) {
  try {
    const payload = transform(formValues(form));
    await api(path, { method: "POST", body: JSON.stringify(payload) });
    form.reset();
    closeOpenModal(form);
    showToast(successMessage);
    await refreshData();
  } catch (error) {
    showToast(cleanError(error.message));
  }
}

function cleanError(message) {
  try {
    const parsed = JSON.parse(message);
    if (parsed.message) {
      return String(parsed.message).slice(0, 180);
    }
  } catch (error) {
    // Response was not JSON.
  }
  return message.replace(/<[^>]*>/g, "").slice(0, 180) || "Request failed";
}

function wireEvents() {
  $$(".nav-item").forEach((button) => button.addEventListener("click", () => setView(button.dataset.view)));
  $$("[data-view-target]").forEach((button) => button.addEventListener("click", () => setView(button.dataset.viewTarget)));
  $$("[data-open-modal]").forEach((button) => button.addEventListener("click", (event) => {
    if (button.tagName === "A") {
      event.preventDefault();
    }
    window.setTimeout(() => openModal(button.dataset.openModal, button.dataset.authFocus), 0);
  }));
  $$("[data-close-modal]").forEach((button) => button.addEventListener("click", () => closeOpenModal(button)));

  $("#searchInput").addEventListener("input", render);
  $("#jobSort").value = state.jobSort;
  $("#jobSort").addEventListener("change", async (event) => {
    state.jobSort = event.currentTarget.value;
    localStorage.setItem("placementJobSort", state.jobSort);
    await refreshData();
  });
  $("#refreshBtn").addEventListener("click", refreshData);
  $("#refreshApplicationsBtn").addEventListener("click", refreshData);
  $("#applicationFilterForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    state.applicationCompanyFilter = $("#applicationCompanyFilter").value.trim();
    localStorage.setItem("placementApplicationCompanyFilter", state.applicationCompanyFilter);
    await refreshData();
  });
  $("#clearApplicationFilterBtn").addEventListener("click", async () => {
    state.applicationCompanyFilter = "";
    localStorage.removeItem("placementApplicationCompanyFilter");
    $("#applicationCompanyFilter").value = "";
    await refreshData();
  });
  $("#jobsGrid").addEventListener("click", (event) => {
    const applyButton = event.target.closest("[data-apply-company]");
    if (!applyButton) return;
    openApplyModal(decodeURIComponent(applyButton.dataset.applyCompany));
  });
  $("#logoutBtn").addEventListener("click", () => {
    state.session = null;
    localStorage.removeItem("placementSession");
    refreshData();
    showToast("Session cleared");
  });

  $("#loginBtn").addEventListener("click", async () => {
    const username = $("#loginUsername").value.trim();
    const password = $("#loginPassword").value;
    if (!username || !password) {
      showToast("Enter username and password");
      return;
    }
    try {
      state.session = await login(username, password);
      localStorage.setItem("placementSession", JSON.stringify(state.session));
      $("#authModal").close();
      showToast(`${state.session.role === "admin" ? "Admin" : "Student"} signed in`);
      await refreshData();
    } catch (error) {
      showToast(cleanError(error.message) || "Invalid credentials");
    }
  });

  $("#registerBtn").addEventListener("click", async () => {
    const email = $("#registerEmail").value.trim();
    const password = $("#registerPassword").value;
    const role = $("#registerRole").value;
    const path = role === "admin" ? "/api/auth/register-admin" : "/api/auth/register";

    try {
      await api(path, {
        method: "POST",
        body: JSON.stringify({
          username: email,
          email,
          password,
          fullName: $("#registerName").value.trim()
        })
      });
      state.session = await login(email, password);
      localStorage.setItem("placementSession", JSON.stringify(state.session));
      $("#authModal").close();
      showToast(`${role === "admin" ? "Admin" : "Student"} account created and signed in`);
      await refreshData();
    } catch (error) {
      showToast(cleanError(error.message));
    }
  });

  $$("[data-auth-switch]").forEach((link) => link.addEventListener("click", (event) => {
    event.preventDefault();
    switchAuthPanel(link.dataset.authSwitch);
    if (link.dataset.authSwitch === "register") {
      $("#registerName")?.focus();
    } else {
      $("#loginUsername")?.focus();
    }
  }));

  $("#companyForm").addEventListener("submit", (event) => {
    event.preventDefault();
    handleFormSubmit(event.currentTarget, "/api/admin/company", "Company added");
  });

  $("#jobForm").addEventListener("submit", (event) => {
    event.preventDefault();
    handleFormSubmit(event.currentTarget, "/api/admin/job", "Job drive created", (data) => ({
      ...data,
      salary: Number(data.salary),
      minCgpa: Number(data.minCgpa),
      lastDateToApply: toBackendDate(data.lastDateToApply)
    }));
  });

  $("#announcementForm").addEventListener("submit", (event) => {
    event.preventDefault();
    handleFormSubmit(event.currentTarget, "/api/admin/add-announcement", "Announcement published");
  });

  $("#emailForm").addEventListener("submit", (event) => {
    event.preventDefault();
    handleFormSubmit(event.currentTarget, "/api/admin/add-email", "Student email allowed", (data) => ({
      email: data.email.trim().toLowerCase()
    }));
  });

  $("#applyForm").addEventListener("submit", (event) => {
    event.preventDefault();
    handleFormSubmit(event.currentTarget, "/api/student/application", "Application submitted", (data) => ({
      ...data,
      cgpa: Number(data.cgpa)
    }));
  });

  $("#profileForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const form = event.currentTarget;
    const payload = { ...formValues(form), cgpa: Number(formValues(form).cgpa) };
    try {
      const profile = await api("/api/student/profile", { method: "POST", body: JSON.stringify(payload) });
      state.profile = profile;
      localStorage.setItem("placementProfile", JSON.stringify(profile));
      closeOpenModal(form);
      showToast("Student profile updated");
      render();
    } catch (error) {
      showToast(cleanError(error.message));
    }
  });

  $("#filterForm").addEventListener("submit", async (event) => {
    event.preventDefault();
    const minCgpa = Number(new FormData(event.currentTarget).get("minCgpa"));
    try {
      const students = await api(`/api/admin/students/filter?minCgpa=${encodeURIComponent(minCgpa)}`);
      renderEligible(Array.isArray(students) ? students : []);
    } catch (error) {
      renderEligible(demoData.students.filter((student) => Number(student.cgpa) >= minCgpa));
      showToast("Showing demo eligible students");
    }
  });
}

function renderEligible(students) {
  const container = $("#eligibleStudents");
  if (!students.length) {
    container.innerHTML = `<div class="empty">No eligible students found for this CGPA.</div>`;
    return;
  }
  container.innerHTML = students.map((student) => `
    <div class="list-row">
      <div>
        <strong>${student.user?.fullName || student.branch || "Student"}</strong>
        <span>${student.branch || "Branch"} - CGPA ${student.cgpa || "0"}</span>
      </div>
      <span class="chip good">Eligible</span>
    </div>
  `).join("");
}

wireEvents();
renderEligible(demoData.students);
setView(window.location.hash.replace("#", "") || "overview");
refreshData();
