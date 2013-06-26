function pu = uniqueperms(vec)
% How many elements in vec?
vec = vec(:); % make it always a column vector
n = length(vec);

% how many unique elements in vec?
uvec = unique(vec);
nu = length(uvec);

% any special cases?
if isempty(vec)
  pu = [];
elseif nu == 1
  % there was only one unique element, possibly replicated.
  pu = vec';
elseif n == nu
  % all the elements are unique. Just call perms
  pu = perms(vec);
else
  % 2 or more elements, at least one rep
  pu = cell(nu,1);
  for i = 1:nu
    v = vec;
    ind = find(v==uvec(i),1,'first');
    v(ind) = [];
    temp = uniqueperms(v);
    pu{i} = [repmat(uvec(i),size(temp,1),1),temp];
  end
  pu = cell2mat(pu);
end 