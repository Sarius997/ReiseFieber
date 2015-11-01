Select kundenreise.id, kunden.id, kunden.nachname, kunden.vorname, reise.id, reise.name, reise.ziel
from kundenreise JOIN kunden on kunden.id=kundenreise.k_id 
JOIN reise on reise.id=kundenreise.r_id