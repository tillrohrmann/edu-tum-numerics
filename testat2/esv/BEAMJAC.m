function J = BEAMJAC(t,y)

% Berechnet die Jacobi-Matrix fuer den "stiff beam"
% siehe Hairer, Wanner II, S. 8-10
%
% Parameter:
%   t      (input)  : Zeit
%   y      (input)  : aktuelle Positionen und Geschwindigkeiten
%   J      (output) : Wert der Jacobi-Matrix

% Anzahl der Diskretisierungspunkte des Stabs
S = length(y)/2;

help = ones(S,1);
GINV = spdiags([-help,2*help,-help],[-1,0,1],S,S);
clear help;
GINV(S,S) = 3;

GDISC = -GINV;
GDISC(1,1) = -3;
GDISC(S,S) = -1;
	
F = FUNC(t);
v = S^4 * (GDISC*y(1:S,1)) ...
		+ S^2 * (F(2)*cos(y(1:S,1)) - F(1)*sin(y(1:S,1)));
GRADv = S^4*GDISC + S^2*diag(-F(2)*sin(y(1:S,1)) - F(1)*cos(y(1:S,1)));

clear i;
MAT = diag(exp(1i*y(1:S,1))) * GINV * diag(exp(-1i*y(1:S,1)));
clear GINV;
C = real(MAT);
D = imag(MAT);

DMATv = spdiags([1i*diag(MAT,-1).*(v(2:S)-v(1:S-1)), zeros(S-1,1),...
								 1i*diag(MAT,1).*(v(1:S-1)-v(2:S)); 0 0 0],[-1,0,1],S,S);
GRADCv = real(DMATv);
GRADDv = imag(DMATv);

w = D*v + y(S+1:2*S).^2;
GRADw = GRADDv + D*GRADv;
u = C\w;

DMATu = spdiags([1i*diag(MAT,-1).*(u(2:S)-u(1:S-1)), zeros(S-1,1),...
								 1i*diag(MAT,1).*(u(1:S-1)-u(2:S)); 0 0 0],[-1,0,1],S,S);
GRADCu = real(DMATu);
GRADDu = imag(DMATu); 
GRADu = C\(GRADw - GRADCu);
GRAD2 = GRADCv + C*GRADv + GRADDu + D*GRADu;
J = [zeros(S,S), eye(S); GRAD2, 2*D*CINV*diag(y(S+1:2*S,1))];

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
function F = FUNC(t)

tmp = 1.5*(sin(t))^2*(t<=pi);

F(1) = -tmp;
F(2) = tmp;
