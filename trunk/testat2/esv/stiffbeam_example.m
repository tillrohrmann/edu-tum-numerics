%% 
%   Stiff beam 
% 
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
clear all; close all;

%% Define ODE.
% parameter
T = 5; 
S = 4;

% right hand side
f = @(t,y)(BEAMODE(t,y));

% Jacobian of the right hand side
df = @JAC;

% initial conditions
t0 = 0;
y00 = zeros(S,1);
v00 = zeros(S,1);
y0 = [y00; v00];

%% Solve ODE and plot results.
% number of time steps
Nvec = [100;200];

for ii = 1:length(Nvec)
  % different number of space steps 
  N = Nvec(ii);
  % step size
  h = (T-t0)/N;
  
  % Crank-Nicholson with Newton iteration
  
  [t,y,iter] = OSM(t0,y0,h,N,@CN_STEP_NEWTON,f,df);
   
  % graphical output 
  figure(ii)
  subplot(2,2,1)
  plotbeam(N,S,y,t);
  hold on;
  str = sprintf('Crank-Nicolson + Newton, \n%d time steps',N);
  title(str, 'fontsize', 14);
  set(gca, 'fontsize', 14);

  subplot(2,2,3)
  plot(1:N,iter,'b-','Linewidth',2)
  hold on;
  title('Number of Newton iterations','fontsize', 14);
  set(gca,'fontsize',14);
  xlim([1 N]);
  
  % Crank-Nicholson with fixed point iteration
  [t,y,iter] = OSM(t0,y0,h,N,@CN_STEP_FIXEDPOINT,f);
   
  % graphical output
  figure(ii)
  subplot(2,2,2)
  plotbeam(N,S,y,t);
  hold on;
  str = sprintf('Crank-Nicolson + Fixed point, \n%d time steps',N);
  title(str,'fontsize',14);
  set(gca,'fontsize',14);

  subplot(2,2,4)
  plot(1:N,iter,'b-','Linewidth',2)
  hold on;
  title('Number of fixed point iterations', 'fontsize', 14);
  set(gca,'fontsize',14);
  xlim([1 N]);
end