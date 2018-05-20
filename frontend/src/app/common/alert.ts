export function showErrorToast(message: string) {
	const toastContainer = document.querySelector('#alert');
	toastContainer.innerHTML += alertTemplate(message, 'danger');
}

function alertTemplate(message: string, style: string) {
	let template = `
        <div class="alert alert-${style} container" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            ${message}
        </div>
	`;
	return template;
}
