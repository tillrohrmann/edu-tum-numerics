%%
%    Predator-prey model
%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
clear all; close all;

%% Define ODE.
% parameter
a = 1;
b = 1/10;
c = 1/2;
d = 1/30;

% right hand side
f = @(t,y)[a*y(1) - b*y(1)*y(2); -c*y(2) + d*y(1)*y(2)];

% Jacobian of the right hand side
df = @(t,y)[a-b*y(2) -b*y(1);d*y(2) -c+d*y(1)];

% inital conditions
t0 = 0;
y0 = [20;20];

%% Solve ODE and plot results.
% methods
method = {@EULER_STEP; @MOD_EULER_STEP; @HEUN_STEP};
% plot colors
col = {'-b'; '-g'; '-r'};

% time step size
n = 5;
h = 1/(2^n);

% number of time steps
s = 20/h;

for m=1:3

    [t,y] = OSM(t0,y0,h,s,method{m},f);
    
    figure(1)
    plot(t,y(1,:),col{m},'LineWidth',2);
    hold on;
    xlabel('t');
    ylabel('y(1)');
    figure(2)
    plot(t,y(2,:),col{m},'LineWidth',2);
    hold on;
    xlabel('t');
    ylabel('y(2)');
    figure(3)
    plot(y(1,:),y(2,:),col{m},'LineWidth',2);
    hold on;
    xlabel('y(1)');
    ylabel('y(2)');
end