// toast.js

function showToastMsg(message) {
    // 检查并创建 toast HTML 结构
    let toastContainer = document.querySelector('.toast-container');

    if (!toastContainer) {
        // 创建容器
        toastContainer = document.createElement('div');
        toastContainer.className = 'toast-container position-fixed bottom-0 end-0 p-3';
        document.body.appendChild(toastContainer);
    }

    // 创建 toast 元素
    const toastWrapper = document.createElement('div');
    toastWrapper.className = 'toast align-items-center';
    toastWrapper.setAttribute('role', 'alert');
    toastWrapper.setAttribute('aria-live', 'assertive');
    toastWrapper.setAttribute('aria-atomic', 'true');

    toastWrapper.innerHTML = `
        <div class="d-flex">
            <div class="toast-body">${message}</div>
            <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    `;

    toastContainer.appendChild(toastWrapper);

    // 使用 Bootstrap 的 Toast 实例并显示
    const toast = new bootstrap.Toast(toastWrapper);
    toast.show();

    // 自动移除 DOM 中的 toast 元素（可选）
    toastWrapper.addEventListener('hidden.bs.toast', () => {
        toastWrapper.remove();
    });
}
