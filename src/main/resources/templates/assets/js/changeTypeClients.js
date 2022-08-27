const el = document.getElementById('select_type_client');
const individual = document.getElementById('individual');
const legal = document.getElementById('legal');
el.addEventListener('change', function handleChange(event) {
    if (event.target.value === 'INDIVIDUAL') {
        legal.style.display = 'none';
        individual.style.display = 'block';
    } else {
        legal.style.display = 'block';
        individual.style.display = 'none';
    }
});