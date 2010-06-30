function plotbeam(n,S,y,t)

% Funktion zum Zeichnen des schwingenden Stabes
%
% Eingabeparameter:
%   n: Anzahl der Zeitschritte
%   S: Anzahl der Ortsdiskretisierungen
%   y: Lösungsvektor(en)
%   t: Zeitvektor

dS = 1/S;
% Anfangskoordinaten berechnen
xxold(1) = 0; yyold(1) = 0;
for j = 1 : S
	xxold(j+1) = xxold(j) + dS*cos(y(j,1));
	yyold(j+1) = yyold(j) + dS*sin(y(j,1));
end

cla;
set(gca,'YDir','reverse')
axis off;
view([-5,-5,6])
for i = 1:n
    % Berechnung der (x,y)-Punkte
        xx(1) = 0; yy(1) = 0;
        for j = 1 : S
            xx(j+1) = xx(j) + dS*cos(y(j,i));
            yy(j+1) = yy(j) + dS*sin(y(j,i));
        end
	    hold on
	    help1 = ones(1,S+1);
        x_plot = [t(i)*help1, t(i+1)*help1];
        y_plot = [yyold, yy(S+1:-1:1)];
        z_plot = [xxold, xx(S+1:-1:1)];
        fill3(x_plot,y_plot,z_plot,[0.7, 1.0, 0.7])                 
        yyold = yy;
        xxold = xx;
end
