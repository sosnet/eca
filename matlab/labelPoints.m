function[pointGrid] = labelPoints(pointGrid, N, K, s, index)

% index = 1;
% 
% N = 10;
% K = 7;
% 
% pointGrid = createGrid(N, K);

grid_x = meshgrid(1:N, 1:N);
grid_y = meshgrid(1:N, 1:N)';
x = grid_x(pointGrid == 1);
y = grid_y(pointGrid == 1);
p = [x,y];

k = convhull(x,y);
s = flipud([x(k), y(k)]);

angles = zeros(K, 1);
distances = zeros(K, 1);

v = s(index+1, :) - s(index, :);
pointGrid(s(index, 2), s(index, 1)) = 1;
pointGrid(s(index+1, 2), s(index+1, 1)) = 2;

for i=1:K
    if p(i,:) == s(index, :)
        continue;
    end
    if p(i,:) == s(index+1, :)
        w = p(i,:) - s(index, :);
        distances(i) = norm(w);
        continue;
    end
    w = p(i,:) - s(index, :);
    angles(i) = acos(dot(v,w) / (norm(v)*norm(w)));
%     if angles(i) == 0
%       angles(i) = -1;      
%       distances(i) = norm(w);
%     else 
%       distances(i) = 0;
%     end
    distances(i) = norm(w);
end

[value2, index2] = sort(distances, 1, 'ascend');
x = x(index2);
y = y(index2);
angles = angles(index2);
[value, index] = sort(angles, 1, 'ascend');
%[value2, index2] = sort(distances, 1, 'ascend');

for i=3:K
    %if angles(index(i)) > 0
        pointGrid(y(index(i)), x(index(i))) = i;        
    %else
%         if distances(index(i)) > distances(index(i-1))
%             pointGrid(y(index(i)), x(index(i))) = i; 
% %         else
% %             pointGrid(y(index(i)), x(index(i))) = i-1; 
% %             pointGrid(y(index(i-1)), x(index(i-1))) = i; 
%         end
%     end
end
