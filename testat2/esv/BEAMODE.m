function y = BEAMODE(t,y)

% Berechnet die rechte Seite fuer den "stiff beam"
% siehe Hairer, Wanner II, S. 8-10
%
% Eingabe-Parameter:
%   t      (input)  : Zeit
%   y      (input)  : aktuelle Positionen und Geschwindigkeiten
%   y      (output) : Wert der rechten Seite

% Anzahl der Diskretisierungspunkte des Stabs
S = length(y)/2;

help = ones(S,1);
GINV = spdiags([-help,2*help,-help],[-1,0,1],S,S);
GINV(1,1) = 3;
GINV(S,S) = 1;
clear help;

F = FUNC(t);
v = S^4 * (-GINV*y(1:S,1)) ...
		+ S^2 * (F(2)*cos(y(1:S,1)) - F(1)*sin(y(1:S,1)));

GINV(1,1) = 1;
GINV(S,S) = 3;

clear i;
MAT = diag(exp(1i*y(1:S,1))) * GINV * diag(exp(-1i*y(1:S,1)));
clear GINV;
C = real(MAT);
D = imag(MAT);
clear MAT;

help = y(S+1:2*S,1).^2;
w = D*v + help;
u = C\w;

y(1:S,1) = y(S+1:2*S,1);
y(S+1:2*S,1) = C*v + D*u;

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function F = FUNC(t)

tmp = 1.5*(sin(t))^2*(t<=pi);

F(1) = -tmp;
F(2) = tmp;
